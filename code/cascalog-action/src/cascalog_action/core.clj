(ns cascalog-action.core)



(comment

  (require '[cascalog.api :refer :all]
           '[cascalog.ops :as c]
           '[adgoji.cascading.scheme :refer :all])

  ;; hello world
  (?- (stdout) [[1 2]])





  ;; Number of Lines with Clojure in all Github events of March 2013
  (??- (<- [?count]
           ((hfs-json "../../data/clojure-march-2013.json") ?json)
           (c/count ?count)))





  (def clojure-events (hfs-json "../../data/clojure-march-2013.json"))

  ;; All Events (this will kill the right buffer :-)
  ;; (?- (stdout) clojure-events)




  ;; How many Pull Requests?
  (??- (<- [?count]
           (clojure-events ?event)
           (get-in ?event ["type"] :> "PullRequestEvent")
           (c/count ?count)))








  ;; How many Watch Events?
  (??- (<- [?count]
           (clojure-events ?event)
           (get-in ?event ["type"] :> "WatchEvent")
           (c/count ?count)
           ))







  (?<- (stdout)
       [?name ?count]
       (clojure-events ?event)
       (get-in ?event ["actor_attributes" "login"] :> ?name)
       (c/count ?count)

       )







  ;; How many unique 'Clojure' users?
  (??- (<- [?count]
           (clojure-events ?event)
           (get-in ?event ["actor_attributes" "login"] :> ?name)
           (c/distinct-count ?name :> ?count)
           ))





  ;; Github users generating most events in March 2013
  (?- (stdout) (c/first-n (<- [?name ?count]
                              (clojure-events ?event)
                              (get-in ?event ["actor_attributes" "login"] :> ?name)
                              (c/count ?count))
                          1000
                          :sort ["?count"]
                          :reverse true))








  ;; Github repos generating most events in March 2013
  (?- (stdout) (c/first-n (<- [?name ?count]
                              (clojure-events ?event)
                              (get-in ?event ["repository" "name"] :> ?name)
                              (c/count ?count))
                          1000
                          :sort ["?count"]
                          :reverse true))





  (defn like [location query]

    (re-matches query location))


(?<- (hfs-textline "join.txt")  [?username]
    (clojure-events ?event)
    (clojure-events ?event)
    (get-in ?event ["repository" "name"] :> ?repo)
    (get-in ?event ["actor_attributes" "login"] :> ?username)
    (= ?username ?repo)
    )

  ;; Where are people coming from?
  (?- (hfs-textline "people.txt")
      (c/first-n (<-)
                          1000
                          :sort ["?count"]
                          :reverse true
                          ))


  )




;; Ideasss???? More questions??
