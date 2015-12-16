(ns site.server
  (:require [clojure.string :as str]
            [hiccup.core :refer [html]]
            [hiccup.page :as hp]
            [ring.middleware.resource :as rmr]
            [site.data :refer [strings]]
            [org.httpkit.server :as srv]
            [site.core :refer [index products projects layout contacts]]
            [route-map.core :as rt]))

(defn http [hic]
  {:body    (html hic)
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :status  200})


(def routes
  {:GET #'index
   "about"    {:GET  #'index}
   "products" {:GET #'products}
   "contacts" {:GET #'contacts}
   "projects" {:GET #'projects}})

(defn dispatch [{uri :uri meth :request-method :as req}]
  (if-let [mtch (rt/match [meth uri] routes)]
    (http (layout ((:match mtch) (update req :params merge (:params mtch)))))
    {:body "ups" :method 404}))

(def app (-> #'dispatch
             (rmr/wrap-resource "public")))

(defn start []
  (def stop (srv/run-server #'app {:port 8081})))

(comment
  (stop)
  (start)
  )

