(ns site.projects
  (:require [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash]]))

(defn projects [req]
  [:div
   (navigation {:color :inverse})
   (splash {:title  "Projects" :moto "looking for"})])
