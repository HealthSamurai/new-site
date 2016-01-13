(ns site.projects
  (:require [site.navigation :refer [navigation]]
            [site.data :refer [data find-by-id idata i]]
            [site.formats :refer [load-text] :as fmt]
            [garden.units :refer [px px*]]
            [site.styles :refer [style pbox s-var vh] :as s]
            [site.widgets :refer [grid  splash] :as w]))


(defn project-view [project]
  [:div.project
   (style
    [:.project
     (s/&margin 2 nil)
     (s/&padding 2 nil)
     [:h2 {:line-height (s/vh* 2)}]
     [:h4 {:line-height (s/vh* 2) :margin-top (s/vh* 1)}]
     [:.images {:margin-top (s/vh* 4)
                :width  (px 300)
                :height (px 300)}
      [:.image {:display "inline-block"
                :background-color "#eee"
                :cursor "pointer"
                :margin (px 2)
                :width (px 146)
                :height (px 146)}
       [:&:hover (s-var :color :main :selection)]]]])
   [:div.container
    [:div.row
     [:div.col-md-8
      [:h2 {:id (:id project)} (i project :title) " " [:small (i project :sub-title)]]
      [:div.post (fmt/markdown (or (i project :post) (i project :desc)))]
      [:h4 (idata :text :used-technologies)]
      (w/tags (:tags project) {:inline true})]
     [:div.col-md-4
      [:div.images
       [:div.image]
       [:div.image]
       [:div.image]
       [:div.image]]]]]])

(defn projects [req]
  [:div#projects
   (navigation {:color :inverse})
   (splash {:title (idata :text :projects)
            :moto (idata :text :projects-subtitle)})
   [:div.container (interpose [:hr] (map project-view (data :projects)))]])


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
       [:p (when (:post project) (load-text (str "texts/"(:post project))))]]]]))
