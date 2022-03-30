(ns message-log.events
  (:require
   [re-frame.core :as re-frame]
   [message-log.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::update-db
 (fn [db [_ id val]]
   (assoc-in db [:form id] val)))

(re-frame/reg-event-db
 ::update-list
 (fn [db [_ id]]
   (let [input (conj {:unix-time (.now js/Date)} (:form db))
         messages (get db :msg [])
         merged (conj messages input)]
     (assoc db :msg merged))))

(re-frame/reg-event-db
 ::clear-input
 (fn [db]
   (dissoc db :form)))