(ns awesome-project.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))


(def pic {:uri "https://upload.wikimedia.org/wikipedia/commons/d/de/Bananavarieties.jpg"})

;(defn greeting[] (fn [name] [text (str "Hello " name )]))
(defn greeting [name] [text (str "Hello " name )])
; View is a container that supports layout with flexbox, style, some touch handling,
; and accessibility controls.
; View maps directly to the native view equivalent on whatever platform React Native is running on,
;  whether that is a UIView, <div>, android.view, etc.

; (defn setState
;   "if @atom = true, it write the text, else it writes empty string "
;   [atom text-display]
;   (if @atom
;     [text text-display]
;     [text " " ] ))

(defn Blink
  "create an atom with boolean value true and changes is state every second."
  [text-display]
  (let [showText (r/atom true)]
    (fn []
      (js/setTimeout #(swap! showText not) 1000) ;we used setTimeout instead of setInterval
      (if @showText [text text-display] [text " "])
      )))
(def styles {:bigblue {:color "blue" :fontWeight "bold" :fontSize 30} :red {:color "red"}})
(defn app-root[]
  (fn []
         [view {:style {:align-items "center"} }
           [text {:style {:color "red" :text-align "center"}} "Hello world!"] ;add a formatted text
           [image {:source pic :style {:width 193 :height 110}}] ;add an image
           [greeting "Rexxar"] [greeting "Jaina"] [greeting "Vallera"]
           [Blink "I love to blink"]
           [Blink "Yes blinking is so great"]
           [Blink "Why did they ever take this out of HTML"]
           [Blink "Look at me look at me look at me"]
           ;[text {:style {(styles :bigblue)}} "just bigblue"] to correct
          ]
    )
  )


(defn init []
      (.registerComponent app-registry "AwesomeProject" #(r/reactify-component app-root)))
