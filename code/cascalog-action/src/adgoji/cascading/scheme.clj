(ns adgoji.cascading.scheme
  "Pail function inspired, but mostly copied from "
  (:import [cascading.tuple Fields]
           [org.apache.hadoop.io Text])
  (:require [cascalog.tap :as tap]
            [cheshire.core :as json])
  (:gen-class))

(gen-class :name adgoji.cascading.scheme.EdnLine
           :extends cascading.scheme.hadoop.TextLine
           :prefix "edn-")

(defn edn-source [this flow-process sourceCall]
  (let [context (.getContext sourceCall)]
    (if (not (.. sourceCall getInput (next (aget context 0) (aget context 1))))
      false
      (let [text ^Text (aget context 1)
            split (read-string (String. (.getBytes text) 0 (.getLength text)))
            tuple (.. sourceCall getIncomingEntry getTuple)
            _ (.clear tuple)
            _ (.add tuple ^Object split)]
        true))))

;; Not sure why we need to override this?
(defn edn-sinkPrepare [this flowProcess sinkCall]
  (let [ctx (make-array Object 1)
        _ (aset ctx 0 (Text.))]
    (.setContext sinkCall ctx)))

(defn edn-sink [this flow-process sinkCall]
  (let [context (.getContext sinkCall)
        text (aget context 0)
        ;; What is the best way to deal with JSON as output?
        ; obj (.. sinkCall getOutgoingEntry getTuple (getObject 0))
        tuple (.. sinkCall getOutgoingEntry getTuple)
        obj (map #(.getObject tuple %) (range (.size tuple)))
        line (pr-str obj)
        _ (.set text (.getBytes line))]
        (.. sinkCall getOutput (collect nil text))))

(defn- edn []
  (adgoji.cascading.scheme.EdnLine. (Fields. (into-array Comparable ["edn"]))))

(defn hfs-edn
  [path]
    (tap/hfs-tap (edn) path))

(gen-class :name adgoji.cascading.scheme.JsonLine
           :extends cascading.scheme.hadoop.TextLine
           :prefix "json-")

(defn json-source [this flow-process sourceCall]
  (let [context (.getContext sourceCall)]
    (if (not (.. sourceCall getInput (next (aget context 0) (aget context 1))))
      false
      (let [text ^Text (aget context 1)
            split (json/parse-string (String. (.getBytes text) 0 (.getLength text)))
            tuple (.. sourceCall getIncomingEntry getTuple)
            _ (.clear tuple)
            _ (.add tuple ^Object split)]
        true))))

;; Not sure why we need to override this?
(defn json-sinkPrepare [this flowProcess sinkCall]
  (let [ctx (make-array Object 1)
        _ (aset ctx 0 (Text.))]
    (.setContext sinkCall ctx)))

(defn json-sink [this flow-process sinkCall]
  (let [context (.getContext sinkCall)
        text (aget context 0)
        ;; What is the best way to deal with JSON as output?
        ; obj (.. sinkCall getOutgoingEntry getTuple (getObject 0))
        tuple (.. sinkCall getOutgoingEntry getTuple)
        obj (map #(.getObject tuple %) (range (.size tuple)))
        line (json/generate-string obj)
        _ (.set text (.getBytes line))]
        (.. sinkCall getOutput (collect nil text))))

(defn- json []
  (adgoji.cascading.scheme.JsonLine. (Fields. (into-array Comparable ["edn"]))))

(defn hfs-json
  [path]
    (tap/hfs-tap (json) path))
