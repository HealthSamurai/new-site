(ns site.data)


(def strings
  {:title "Health Samurai"
   :text {:products "Продукты"
          :education "Обучение"
          :projects "Проекты"
          :services "Услуги"
          :contacts "Контакты"}
   :services [{:href "/services" :text "Запуск и обучение"}
              {:href "/services" :text "Запуск и разработка"}
              {:href "/services" :text "Коммерческая поддержка"}]

   :education [{:href "/education" :text "Введение в HL7 FHIR"}
               {:href "/education" :text "HL7 FHIR для продвинутых"}]

   :products [{:id "fhirbase"
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
                        :link "https://aidbox.io/landing.html#/"}]}]})
