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
                   :border-width "3px"
                   :border-color (s/clr :blue)
                   :color (s/clr :blue)
                   :font-weight 600
                   :padding "0 1em"
                   :line-height (s/vh* 1.5)
                   :border-radius "50px"
                   :margin-left (s/vh* 1)}
            [:&:hover {:background (s/clr :blue)
                       :color "white"}]]])

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
             {:padding-bottom (s/vh* 5)
              :background-color "#f1f1f1"}
             [:h1 (:h1 typescale)]
             [:.card {:margin-right (s/vh* 1)
                      :position "relative"
                      :display "block"
                      :color (s/clr :black)
                      :border-radius "4px"
                      ;;:border "2px solid #ddd"
                      :box-shadow (str  "0px 0px 2px 2px " (s/clr :gray))
                      :padding (s/vh* 1)}
              (undecorate)
              [:.svg {:height (s/vh* 15) :width (s/vh* 15) :margin "0 auto"}
               [:path {:stroke "#e5e5e5" :stroke-width 3 :fill "none"}]]
              [:&.major
               {:background-color (s/clr :dark-gray) :color "white"}
               [:.svg [:path {:stroke "#666"}]]
               [:&:hover
                {:box-shadow (str  "0px 0px 2px 2px " (s/clr :orange))}
                [:.svg [:path {:stroke (s/clr :orange)}]]]]
              [:&:hover
               {:box-shadow (str  "0px 0px 2px 2px " (s/clr :blue))}
               [:.svg [:path {:stroke (s/clr :blue)}]]]
              [:h2 :h3 {:text-align "center" :line-height (s/vh* 1) :margin {:top 0 :bottom (s/vh* 2)}}]
              [:h2 (:h1 typescale) (s/&text 500)]
              [:h3 (:h3 typescale)]
              [:p.desc {:min-height (s/vh* 10)} (s/&padding 1)]]])
     [:div.container
      (block-header (url (name items-type)) items-type (count items))
      (apply grid
             (for [p (take 3 items)]
               [[:a.card {:class (when (:major p) "major")
                          :href (url (str (name items-type)) {:# (p :id)})}
                 (let [title (i p :title)] [(if (long-title? title) :h3 :h2) title])
                 [:div.svg (s/svg "fhirbase")]
                 [:p.desc  (i p :abstract)]]]))]]))
#_(cond
    (= (:icon-type p) "hs") [:i.hs-icon {:class (font/fontello-icon-name (p :id))}]
    (= (:icon-type p) "fa") [:i.fa-icon {:class (str "fa-" (p :icon))}]
    (= (:icon-type p) "img") [:img.img {:src (str  "/" (p :icon))}]
    (= (:icon-type p) "svg") [:div.svg (s/svg (:icon p))])

(defn product-list [opts]
  (cards-list (merge opts {:items :products})))


(defn project-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#project-list
     (style [:#project-list
             {:padding-bottom (s/vh* 5)}
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
     [:svg {:fill "none"
            :stroke "#A23836"
            :stroke-miterlimit 10}
      [:#line-4 [:g {:stroke "#ddd"}]]
      [:#line-3 [:g {:stroke "#aaa"}]]
      [:#line-2 [:g {:stroke "#888"}]]
      [:#line-1 [:g {:stroke "#888" :stroke-width 2}]]
      [:.lines
       {:fill "none"
        :stroke "#D2D2D2"
        :stroke-miterlimit 10
        :stroke-dasharray 3.3}]]

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
       :width "420px"}
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
