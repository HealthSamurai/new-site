(ns site.projects
  (:require [site.navigation :refer [navigation]]
            [site.data :refer [data]]
            [markdown.core :as md]
            [garden.units :refer [px px*]]
            [site.styles :refer [style pbox s-var vh]]
            [site.widgets :refer [grid  splash paralax]]))


(defn project-view [project]
  [:div.project
   [:div.container
    [:div.row
     [:div.col-md-9
      [:a {:href (str "/projects/" (:id project))}
       [:h3 (:title project)]]
      [:p (:desc project)]]
     [:div.col-md-3
      [:div.images
       [:div.image]
       [:div.image]
       [:div.image]
       [:div.image]]]]]])

(defn projects [req]
  [:div#projects

   (style
    [:#projects
     [:.project
      {:border {:bottom {:color "#ddd" :style "solid" :width "1px"}}}
      (pbox 2 nil 4 nil)]
     [:.images {:width  (px 200)
                :height (px 200)}
      [:.image {:display "inline-block"
                :background-color "#eee"
                :cursor "pointer"
                :margin (px 2)
                :width (px 96)
                :height (px 96)}
       [:&:hover
        {:background-color (s-var :color :inverse :text :background-color)}]]]])

   (paralax 20
            [:div
             (navigation {:color :inverse})
             (splash {:title (data :text :projects)
                      :moto (data :text :projects-subtitle)})]

            (map project-view (data :projects)))])

(defn find-by-id [id coll]
  (first (filter #(= id (:id %)) coll)))

(find-by-id "medclient" (data :projects))

(defn load-text [f]
  (md/md-to-html-string (slurp (str "resources/texts/" f))))

(defn project [{{id :id} :params :as req}]
  (let [project (find-by-id id (data :projects))
        left-pad 300]
    [:div
     (style
      [:#project
       {:margin {:top (px* 2 vh)}}])
     (navigation {})
     [:div#project
      [:div.container
       [:h1 (:title project)]
       [:br]
       [:p (:desc project)]
       [:h3 "Client"]
       [:p
        [:a {:href (get-in project [:client :href])}
         (get-in project [:client :title])]]
       [:h3 "Used technologies"]
       [:div.tags (for [tag (:tags project)] [:span.btn.btn-default.tag tag])]
       [:br]
       [:p (when (:post project) (load-text (:post project)))]]]]))
