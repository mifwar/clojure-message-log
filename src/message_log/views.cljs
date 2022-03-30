(ns message-log.views
  (:require
   [re-frame.core :as re-frame]
   [message-log.events :as events]
   [message-log.subs :as subs]))

(defn display-messages [{:keys [unix-time message]}]
  [:div
   {:key unix-time}
   [:p message]])

(defn text-input [id label]
  (let [value (re-frame/subscribe [::subs/form id])]
    [:label label]
    [:input.input {:value @value
                   :on-change #(re-frame/dispatch [::events/update-db id (-> % .-target .-value)])
                   :type "text"
                   :placeholder "Type here"}]))

(defn button-send [id label]
  (let [value (re-frame/subscribe [::subs/form id])]
    [:button {:disabled (zero? (count @value))
              :on-click #(do
                           (re-frame/dispatch [::events/update-list id])
                           (re-frame/dispatch [::events/clear-input]))
     } label]))

(defn main-panel []
  (let [messages (re-frame/subscribe [::subs/messages])]
    [:div
     [text-input :message "message"]
     [button-send :message "submit"]
     (map display-messages @messages)]))
