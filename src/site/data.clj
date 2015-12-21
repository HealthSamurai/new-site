(ns site.data
  (:require [site.formats :refer [yaml] :as fmt]))


(def strings
  {:title "Health Samurai"
   :text (:ru (yaml "text.yaml"))
   :contacts  (:ru (yaml "contacts.yaml"))
   :services  (:ru (yaml "services.yaml"))
   :trainings (:ru (yaml "trainings.yaml"))
   :products  (:ru (yaml "products.yaml"))
   :projects [{:id "medclient"
               :title "MedClient EHR"
               :post "medclient.md"
               :href "http://choice-hs.com/#ehr"
               :client {:title "Choice Hospital Systems" :href "http://choice-hs.com"}
               :tags ["Cloud EHR", "Ruby on Rails" "Amazon EC2" "PostgreSQL"]
               :desc "Разработка, сертификация и внедрение в 3-х американских клиниках облачной EHR"}
              {:id "kainos"
               :title "Kainos"
               :tags ["PostgreSQL" "plv8" "consulting"]
               :desc "Доработка Fhirbase под нужды проекта Kainos"}
              {:id "netrika"
               :title "Netrika"
               :tags ["PostgreSQL" "plv8" "consulting"]
               :desc "Консультирование и обучение по HL7 FHIR и внедрение Fhirbase в рамках региональной шины"}
              {:id "miac"
               :title "MIAC"
               :desc "Консультирование и обучение по HL7 FHIR и внедрение Fhirbase в рамках региональной шины"}]
   })

(defn data [& ks]
  (get-in strings ks))

(defn find-by-id [id & ks]
  (first (filter #(= id (:id %)) (get-in strings ks))))

(:contacts strings)

