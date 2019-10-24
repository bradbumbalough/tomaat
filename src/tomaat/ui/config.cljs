(ns tomaat.ui.config
  "The config screen shows settings for Tomaat"
  (:require [goog.string :as gstring]))

(defn token-input
  [{:keys [disabled? on-change token]}]
  [:input#user-token.form__input
    {:type      "text"
     :value     token
     :autoFocus true
     :disabled  disabled?
     :on-change on-change}])

(defn toggle [{:keys [disabled? checked? on-change id]}]
  [:input.label__input
   {:type      "checkbox"
    :id        id
    :checked   checked?
    :disabled  disabled?
    :on-change on-change}])

(defn form [{:keys [disabled? update token slack-me? play-sound? dnd?]}]
  [:form.form
   [:label.form__label {:htmlFor "user-token"} "Slack Token"]
   [token-input
    {:disabled? disabled?
     :update    update
     :token     token
     :on-change #(update :token (.. % -currentTarget -value))}]
   
   [:label.form__label.label.form__toggle
    {:htmlFor "notify"
     :class   (when slack-me? "form__toggle--checked")}
    "Slack me when time is up"
    [toggle
     {:id        "notify"
      :disabled? disabled?
      :checked?  slack-me?
      :on-change #(update :slack-me (.. % -currentTarget -checked))}]]
   
   [:label.form__label.label.form__toggle
    {:htmlFor "dnd"
     :class   (when dnd? "form__toggle--checked")}
    "Pause Slack notifications"
    [toggle
     {:id        "dnd"
      :disabled? disabled?
      :checked?  dnd?
      :on-change #(update :dnd (.. % -currentTarget -checked))}]]

   [:label.form__label.label.form__toggle
    {:htmlFor "play-sound"
     :class   (when play-sound? "form__toggle--checked")}
    "Play sound when time is up"
    [toggle
     {:id        "play-sound"
      :disabled? disabled?
      :checked?  play-sound?
      :on-change #(update :play-sound (.. % -currentTarget -checked))}]]])

(defn config-screen [{:keys [toggle started? update data]}]
  [:div.screen__config.screen__state
   [:h1 "Settings"]
   [form {:disabled?   started?
          :update      update
          :token       (get data :token "")
          :slack-me?   (get data :slack-me false)
          :dnd?        (get data :dnd false)       
          :play-sound? (get data :play-sound false)}]
   [:button.link
    {:on-click toggle
     :type     "button"}
    (gstring/unescapeEntities "&laquo; back")]])
