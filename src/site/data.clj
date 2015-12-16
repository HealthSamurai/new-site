(ns site.data)


(def strings
  {:title "Health Samurai"
   :text {:products "Продукты"
          :education "Обучение"
          :projects "Проекты"
          :services "Услуги"
          :trainings "Тренинги"
          :contacts "Контакты"}

   :contacts [{:href "/" :title "USA: +1 (818) 731-12-79"}
              {:href "/" :title "Russia: +7 (812) 919-00-25"}
              {:href "mailto:hello@health-samurai.io" :text "mailto:hello@health-samurai.io"}]

   :services [{:id "start"
               :title "Запуск и обучение"}
              {:id "dev"
               :title "Разработка"}
              {:id "support"
               :title "Коммерческая поддержка"}]

   :trainings [{:id "start"
                :title "Основы FHIR"
                :desc "Восьмичасовой семинар по основным особенностям стандарта HL7 FHIR и экосистеме вокруг стандарта."}
               {:id "fhirbase"
                :title "Анатомия fhirbase"
                :desc "Тренинг по архитектуре и использованию Fhirbase в разработке решений для здравоохранения."}
               {:id "devops"
                :title "Введение в DevOps"
                :desc "Двухдневный тренинг по подходам и инструментам построения Continuouse Delivery Pipline"}]

   :education [{:href "/education" :title "Введение в HL7 FHIR"}
               {:href "/education" :title "HL7 FHIR для продвинутых"}]

   :projects [{:id "medclient" :title "MedClient EHR"
               :desc "Разработка, сертификация и внедрение в 3-х американских клиниках облачной EHR"}
              {:id "kainos" :title "Kainos"
               :desc "Доработка Fhirbase под нужды проекта Kainos"}
              {:id "netrika" :title "Netrika"
               :desc "Консультирование и обучение по HL7 FHIR и внедрение Fhirbase в рамках региональной шины"}
              {:id "miac" :title "MIAC"
               :desc "Консультирование и обучение по HL7 FHIR и внедрение Fhirbase в рамках региональной шины"}]

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
                        :link "https://aidbox.io/landing.html#/"}]}
              {:id "aidbox"
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
              ]})
