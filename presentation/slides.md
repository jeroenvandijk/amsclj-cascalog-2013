# clojure-cascalog-2013-april

!SLIDE

![hadoop](images/hadoop.png) 

# Hadoop made easy

<br />

![clojure](images/clojure.png)

!SLIDE left

# About 

* Jeroen van Dijk, CTO ADGoji (jeroen.vandijk@millmobile.com)
* Full time Clojure user since last year april
* Full time Cascalog/Hadoop user since june last year

!SLIDE

### We are looking for people!
http://www.adgoji.com/jobs

![iphone](images/iphone.png)

!NOTES

 * a note

!SLIDE

}}} images/cascalog-features.png

!NOTES

 * All of this still true

!SLIDE

## Structure of a query (1)

``` clojure
  (let [people [["Someone" 13]]]
      (<- 
        [?name ?count]                  ;; Output fields, implicity also define what 
                                        ;; variables to group by
    
        (people ?name ?age _ )          ;; Input tap, binds tuples from the tap to 
                                        ;; logic vars. Taps can be any Cascading Tap,
                                        ;; another query or a vector

        (* ?age 2 :> ?age-doubled))     ;; Operators, apply (Clojure) functions to a 
                                        ;; var and bind the outcome to a new var?

        (> ?age 0)                      ;; Filters, when no variables are bound it act
                                        ;; as a filter
        (* ?age ?age-doubled :> 10)     ;; Another Filter, filter everything that 
    
        (c/count ?count)                ;; Aggregators
      ))
```

!SLIDE

## Structure of a query (2)

``` clojure
  ;; Output tap can be any Cascading Tap
  (?- (stdout) query)

  (?<- (stdout) [?var1 ?var2] (query ?var1 ?var2))
  (?<- (hfs-textline "text-file") [?var1 ?var2] (query ?var1 ?var2))

  ;; Can also assigned to a Clojure vector
  (let [in-memory-data (??- query)]
    (println in-memory-data))
```

!SLIDE left

## More details about Cascalog

Nathan Marz blog (http://nathanmarz.com/blog)

* [/introducing-cascalog-a-clojure-based-query-language-for-hado.html](http://nathanmarz.com/blog/introducing-cascalog-a-clojure-based-query-language-for-hado.html)
* [/new-cascalog-features-outer-joins-combiners-sorting-and-more.html](http://nathanmarz.com/blog/new-cascalog-features-outer-joins-combiners-sorting-and-more.html)

Github Wiki

* [https://github.com/nathanmarz/cascalog/wiki](https://github.com/nathanmarz/cascalog/wiki)

Mailing list

* [http://groups.google.com/group/cascalog-user](http://groups.google.com/group/cascalog-user)

!SLIDE top-right

# Nathan Marz book

}}} images/big_data_book.jpg::http://www.manning.com/marz/

!SLIDE left

## Useful Cascalog libraries/tools

* Midje-cascalog for unit testing
* Cascalog-Checkpoints for complex workflows
* Lemur for launching jobs on Amazon's ElasticMapreduce

!SLIDE bottom-left

# Let's tackle a Big Data problem ;-)

}}} images/bigdata.jpg

!SLIDE bottom-left

# All events in March of 2013 with 'Clojure'

}}} images/github-archive.png

!SLIDE left

## What do we want to know?

- Who are the most active Clojurians?
- What are the most popular Clojure Github repos?
- Where are the most Clojurians located?

!NOTES

Approach use Github API to find top 1000 Clojure projects by searching for "Clojure"


