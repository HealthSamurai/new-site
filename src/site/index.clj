(ns site.index
  (:require [site.styles :refer [style vh s-var undecorate] :as s]
            [garden.units :refer [px px*]]
            [site.widgets :refer [splash grid] :as w]
            [site.routes :refer [url]]
            [site.formats :as fmt]
            [site.data :refer [data i idata]]
            [site.font :as font]
            [site.navigation :refer [navigation]]))



(defn block-header [href key & [cnt]]
  [:div.row.block-header
   (style [:.block-header (s/&margin 4 nil 1 nil)
           [:a (s/&unstyle-links)]
           [:.btn {:vertical-align "middle"
                   :margin-left (s/vh* 1)}]])

   [:a {:href href} [:h3 (idata :text key) " "
                     (when cnt
                       [:button.btn.btn-default (str  (idata :text :all) " " cnt)])]]])


(defn long-title? [title]
  (> (count title) 15))



(defn cards-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        items-type (:items opts)
        items (data items-type)
        typescale (s-var :typescale (:typescale props))]
    [:div#card-list
     (style [:#card-list
             [:h1 (:h1 typescale)]
             [:.logo {:display "block"
                      :position "relative"
                      :overflow "hidden"
                      :padding (s/vh* 1)
                      :height (s/vh* 12)
                      :margin   {:right (px 10) :bottom (s/vh* 1)}
                      :color    (s-var :color :main :muted :color)
                      :border   "1px solid #ddd"}
              (undecorate)
              [:&:hover
               (merge (:selection palette) {:background "transparent"})
               [:.svg [:path {:fill (s/s-var :color :main :selection :color)}]]]
              [:.svg {:height (s/vh* 10)
                      :width (s/vh* 10)
                      :display "block"
                      :position "absolute"
                      :bottom (s/vh* 1)
                      :right "-50px"}]
              [:.hs-icon {:display "block"
                          :position "absolute"
                          :bottom (s/vh* 1)
                          :right "-50px"}
               (s/&text :right)
               (s/&font-scale 10 10)]
              [:h2 (merge (:h1 typescale)
                          {:text-align "left"
                           :position "absolute"
                           :left (s/vh* 1)
                           :bottom (s/vh* 1)
                           :color    (s/color :selection)
                           :line-height (s/vh* 1)})
               (s/&text 500)]
              [:h3 (merge (:h3 typescale)
                          {:text-align "left"
                           :position "absolute"
                           :bottom (s/vh* 0.5)
                           :left (s/vh* 1)
                           :color (s-var :color :main :selection :color)
                           :line-height (px* 1 vh)})]]
             [:p.desc (s/&padding 0.5 1)]])
     [:div.container
      (block-header (url (str items-type)) items-type (count items))
      (apply grid
             (for [p (take 3 items)]
               [[:a.logo {:href (url (str (name items-type)) {:# (p :id)})}
                 (cond
                   (= (:icon-type p) "hs") [:i.hs-icon {:class (font/fontello-icon-name (p :id))}]
                   (= (:icon-type p) "fa") [:i.fa-icon {:class (str "fa-" (p :icon))}]
                   (= (:icon-type p) "img") [:img.img {:src (str  "/" (p :icon))}]
                   (= (:icon-type p) "svg") [:div.svg (s/svg (:icon p))])

                 (let [title (i p :title)]
                   (if (long-title? title)
                     [:h3 title]
                     [:h2 title]))]
                [:p.desc  (i p :abstract)]]))]]))


(defn product-list [opts]
  (cards-list (merge opts {:items :products})))


(defn project-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#project-list
     (style [:#project-list
             [:h1 (:h1 typescale)]
             [:.list-item (merge (:text palette)
                                 {:display "block"
                                  :border  {:left  {:width (px 3) :style "solid" :color "#ddd"}}})
              (s/&padding nil 2)
              (s/&margin 0 nil 1 nil)
              (undecorate)
              [:&:hover (:selection palette)
               [:p (:text palette)]]]])
     [:div.container
      (block-header (url "projects") :projects (count (data :projects)))
      (for [p (take 3 (data :projects))]
        [:a.list-item.row {:href (url "projects" {:# (:id p)})}
         [:h3 (i p :title)]
         [:p  (i p :desc)]])]]))

(defn training-list [opts]
  (cards-list (merge opts {:items :trainings})))


(defn partnerhsip-list [opts]
  [:div#partnership
   (style [:#partnership
           [:.items-list
            [:a.list-item
             {:display "block"
              :vertical-align "middle"
              :border  {:width (px 3)}}
             (s/&unstyle-links)
             (s/&padding 1)
             (s/&border :top)
             [:&:hover
              (s-var :color :main :selection)
              [:p {:color (s-var :color :main :text :color)}]
              [:.fa {:color (s-var :color :main :selection :color)}]]
             [:.fa {:float "left"
                    :display "inline-block"
                    :color "#888"
                    :width "auto"
                    :vertical-align "middle"
                    :line-height (s/vh* 4)
                    :font-size (s/vh* 3)}
              (s/&margin nil 2 nil nil)]
             [:.desc
              {:display "inline-block"
               :vertical-align "middle"}]]]])
   [:div.container
    (block-header (url "services") :partnership (count (data :services)))
    [:div.items-list
     (for [x (data :services)]
       [:a.row.list-item {:href (url "services" {:# (:id x)})}
        (w/fa-icon (or (:icon x) "rocket"))
        [:div.desc
         [:h4 (i x :title)]
         [:p  (i x :description)]]])]]])

(defn promo []
  [:div
   (style
    [:#promo
     (s/&padding 9 0 5 0)

     [:.promo-header
      (s/&font-scale 2.3 3)
      (s/&text :center 600)
      (s/&center-block "20em")
      [:em {:color (s/color :selection)}]]

     [:.promo-sub-header
      {:opacity 0.88}
      (s/&padding 1 nil)
      (s/&font-scale 1.1 2)
      (s/&center-block "35em")
      (s/&text :center 600)]])
   [:div#promo
    [:div.container
     [:h1.promo-header [:span (fmt/markdown (idata :text :promo) )]]
     [:p.promo-sub-header [:span (idata :text :promo-subtitle)]]
     (s/svg "bg")]]])

(defn blog-frame [url]
  [:iframe.blogframe
   {:src url
    :allowtransparency "true"
    :frameborder "0"
    :title "Embedded story"
    :width "400"
    :height "377"}])

(defn blog-block [opts]
  [:div.container-fluid.posts
   (block-header (url "#") :blog )
   (s/style
    [:.posts
     {:text-align "center"}
     (s/&margin 2 0 4 0)
     [:.post
      {:display "inline-block"
       :width "430px"}
      (s/&padding 1)
      [:.blogframe
       {:display "block"
        :max-width "100%"
        :min-width "220px"
        :padding 0
        :position "static"
        :visibility "visible"
        :border-radius "5px"
        :border "1px solid #ddd"
        :box-shadow "rgba(0, 0, 0, 0.15) 0px 1px 3px"}]]])
   (for [post (data :blog)]
     [:div.post (blog-frame (:url post))])])

(defn index [req]
  [:div#index
   (navigation {:color :main :request req})
   (style [:#index [:em {:font-style "normal"}]])
   (promo)
   (product-list  {})
   (project-list  {})
   (training-list  {})
   (partnerhsip-list {})
   (repeat 4 [:br])
   (w/call-to-action {:title (idata :text :partnership-target) :moto ""})
   (blog-block {})
   ])
