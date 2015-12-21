(ns site.data
  (:require [site.formats :refer [yaml] :as fmt]))


(def strings
  {:title "Health Samurai"
   :text {:products "Продукты"
          :education "Обучение"
          :projects "Проекты"
          :projects-subtitle "Вместе с нашими клиентам мы создаем медицинские системы нового поколения, которые используются в разных частях мира."
          :services "Услуги"
          :trainings "Тренинги"
          :trainings-subtitle "Мы проводим консультации и тренинги"
          :contacts "Контакты"
          :contacts-subtitle "У нас есть представительства в двух странах и мы всегда готовы встретитсья и обсудить ваш проект."
          :partnership "Cотрудничествo"
          :partnership-subtitle "Консалтинг и разработка"}

   :contacts {:offices [{:id "us"
                         :phone "+1 (818) 731-12-79"
                         :country "США"
                         :city "Лос Анджелес"
                         :gmap "https://www.google.com/maps/embed/v1/place?q=USA%20832%20Hermosa%20Ave%20Hermosa%20Beach%2C%20CA%2090254&key=AIzaSyD2dOMXHt6-O_u74nXCdiUetJbAuSFC4Dc" 
                         :address "USA 832 Hermosa Ave Hermosa Beach, CA 90254"}
                        {:id "russia"
                         :phone "+7 (812) 919-00-25"
                         :country "Россия"
                         :gmap "https://www.google.com/maps/embed/v1/place?q=%D0%91%D0%BE%D0%BB%D1%8C%D1%88%D0%B0%D1%8F%20%D0%9C%D0%BE%D1%80%D1%81%D0%BA%D0%B0%D1%8F%20%D1%83%D0%BB%D0%B8%D1%86%D0%B0%2C%20%D0%A1%D0%B0%D0%BD%D0%BA%D1%82-%D0%9F%D0%B5%D1%82%D0%B5%D1%80%D0%B1%D1%83%D1%80%D0%B3%2C%20%D0%B3%D0%BE%D1%80%D0%BE%D0%B4%20%D0%A1%D0%B0%D0%BD%D0%BA%D1%82-%D0%9F%D0%B5%D1%82%D0%B5%D1%80%D0%B1%D1%83%D1%80%D0%B3%2C%20%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D1%8F%2019&key=AIzaSyAdd1qhkePB-cBamLcFQPmIBCp6QCjEkoo" 
                         :city "Санкт-Перербурr"
                         :address "Россия, Санкт-Петербург, ул. Большая морская 19"}]
              :mailto "hello@health-samurai.io"}

   :services (map
              (fn [s] (assoc s :post (fmt/load-text (str "services/" (:id s) ".ru.md"))))
              (:ru (yaml "services/index.yaml")))

   :trainings (:ru (yaml "trainings/index.yaml"))
   
   :education [{:href "/education" :title "Введение в HL7 FHIR"}
               {:href "/education" :title "HL7 FHIR для продвинутых"}]

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

   :products (:ru (yaml "products/index.yaml"))
   :products-old [{:id "fhirbase"
               :labels ["PostgreSQL" "plv8" "HL7 FHIR"]
               :open-source true
               :title "Fhirbase"
               :slogan "Реляционное хранилище медицинских данных на основе стандарта HL7 FHIR."
               :description "Fhirbase - это реляционное хранилище с встроенной моделью данных, соответствующей международному стандарту HL7 FHIR. Fhirbase подходит для построения медицинских информационных системы разных классов: электронные карты (EHR), интеграционные ”шины”, репозитории медицинских данных (реестры), госпитальные системы (HIS), порталы для пациентов и телемедицины."
               :features ["Fhirbase построен на PostgreSQL с использованием языка plv8"
                          "Fhirbase реализует модель данных соответсвующую HL7 FHIR"
                          "Fhirbase имеет простой API для обращения к данным (like REST)"]
               :links [{:type :direct
                        :link "http://fhirbase.github.io"}
                       {:type :github
                        :link "https://github.com/fhirbase"}
                       {:type :google-group
                        :link "https://groups.google.com/forum/#!forum/fhirbase"}]}
              {:id "aidbox"
               :labels ["Amazon AWS" "Fhirbase" "Clojure" "AngularJS"]
               :title "Aidbox"
               :slogan "Реляционное хранилище медицинских данных на основе стандарта HL7 FHIR."
               :description "Aidbox - это платформа \"как сервис\" (PaaS) для разработки медицинских решений на основе HL7 FHIR. Aidbox подходит для построения мобильных и web-решений оперирующих медицинскими данными: электронные карты (EHR), интеграционные ”шины”, репозитории медицинских данных (реестры), госпитальные системы (HIS), порталы для пациентов и телемедицины."
               :features ["Aidbox использует Fhirbase для хранения данных"
                          "Aidbox реализует REST API на основе стандарта HL7 FHIR"
                          "Aidbox решает вопросы Security с использование OAuth"
                          "Aidbox предоставляет хостинг для базы и несложных single page applications"
                          "Aidbox имеет SDK и вы можете интегрироваться с решениями на ваших технологиях (Java, .Net etc...)"]
               :links [{:type :direct
                        :link "https://aidbox.io/landing.html#/"}]}
              {:id "mapper"
               :labels ["Amazon AWS" "Fhirbase" "Clojure" "AngularJS"]
               :title "HL7 mapper"
               :slogan "Реляционное хранилище медицинских данных на основе стандарта HL7 FHIR."
               :description "Aidbox - это платформа \"как сервис\" (PaaS) для разработки медицинских решений на основе HL7 FHIR. Aidbox подходит для построения мобильных и web-решений оперирующих медицинскими данными: электронные карты (EHR), интеграционные ”шины”, репозитории медицинских данных (реестры), госпитальные системы (HIS), порталы для пациентов и телемедицины."
               :features ["Aidbox использует Fhirbase для хранения данных"
                          "Aidbox реализует REST API на основе стандарта HL7 FHIR"
                          "Aidbox решает вопросы Security с использование OAuth"
                          "Aidbox предоставляет хостинг для базы и несложных single page applications"
                          "Aidbox имеет SDK и вы можете интегрироваться с решениями на ваших технологиях (Java, .Net etc...)"]
               :links [{:type :direct
                        :link "https://aidbox.io/landing.html#/"}]}
              {:id "aidbox"
               :open-source true
               :labels ["AngularJS"]
               :title "FormStamp"
               :slogan "Библиотека виджетов на \"чистом\" AngularJS"
               :description "FormStamp - это библиотека виджетов на AngularJS для разработки web-приложений с \"богатым\" клиентом. "
               :features ["FormStamp написан на \"чистом\" AngularJS"
                          "FormStamp имеет встроеный FormBuilder"
                          "FormStamp стилизован на Twitter Bootstrap"
                          "FormStamp имеет минималистичный codebase "]
               :links [{:type :direct
                        :link "http://formstamp.github.io/#/"}]}]})

(defn data [& ks]
  (get-in strings ks))

(defn find-by-id [id & ks]
  (first (filter #(= id (:id %)) (get-in strings ks))))

