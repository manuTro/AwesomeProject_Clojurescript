(ns awesome-project.android.core
  (:require [reagent.core :as r :refer [atom]]
            [clojure.string :as str]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def text-input (r/adapt-react-class (.-TextInput ReactNative)))
(def scrollview (r/adapt-react-class (.-ScrollView ReactNative)))
(def navigator (r/adapt-react-class (.-Navigator ReactNative)))
(def TouchableHighlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def pic {:uri "http://images.mentalfloss.com/sites/default/files/styles/article_640x430/public/istock_000040857118_small.jpg"})

;(defn greeting[] (fn [name] [text (str "Hello " name )]))
(defn greeting [name] [text (str "Hello " name)])
; View is a container that supports layout with flexbox, style, some touch handling,
; and accessibility controls.
; View maps directly to the native view equivalent on whatever platform React Native is running on,
;  whether that is a UIView, <div>, android.view, etc.


(defn Blink
  "create an atom with boolean value true and changes is state every second.
  It check the ratom state. if it is true, it write the text, is it is false it write empty string"
  [text-display]
  (let [showText (r/atom true)]
    (fn []
      (js/setTimeout #(swap! showText not) 1000) ;we used setTimeout instead of setInterval
      (if @showText [text text-display] ; is setState of the tutorial
          [text " "]) ; => else
)))

(def styles {:bigblue {:color "blue" :fontWeight "bold" :fontSize 30} :red {:color "red"}})

(defn pizzaTranslator []
  (let [val (r/atom " ")
        ;pizza (apply str (for [ x (range 0 (count (swap! val str/split #" ")) ) :let [y "🍕 "]] y))
]
    (fn []
      [view {:style {:padding 10}}
       [text-input {:style {:height 40}
                    :type "text"
                    :placeholder "Type here to translate"
                    :value @val
                    :on-change-text  #(reset! val %)
                     ;:on-change-text (fn [e] (reset! val (.-value  e )))
}]
       [text "🍕 " @val]
        ;[text pizza]
])))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn app-root []
  (fn []
    [scrollview {:style {:padding 10}}
     [text {:id "prova" :style {:color "red" :text-align "center"}} "Hello world!"] ;add a formatted text
     [image {:source pic :style {:width 193 :height 110}}] ;add an image
     [pizzaTranslator]
     [greeting "Rexxar"] [greeting "Jaina"] [greeting "Vallera"]
     [Blink "I love to blink"]
     [Blink "Yes blinking is so great"]
     [Blink "Why did they ever take this out of HTML"]
     [Blink "Look at me look at me look at me"]
     [text {:style (styles :bigblue)} "jst bigblue"]
     [text {:style (styles :red)} "jst red"]
     [text {:style (styles :red (styles ::bigblue))} "jst bigblue"]

          ;  [view {:style {:width 50 :height 50 :backgroundColor "powderblue"}}]
          ;  [view {:style {:width 100 :height 100 :backgroundColor "skyblue"}}]
          ;  [view {:style {:width 150 :height 150 :backgroundColor "steelblue"}}]

     [view {:style {:flex 1}}
      [view {:style {:flex 1 :backgroundColor "powderblue"}}]
      [view {:style {:flex 1 :backgroundColor "skyblue"}}]
      [view {:style {:flex 1 :backgroundColor "steelblue"}}]]
     [view {:style {:flex 1 :flexDirection "row"}}
      [view {:style {:width 50 :height 50 :backgroundColor "powderblue"}}]
      [view {:style {:width 50 :height 50 :backgroundColor "skyblue"}}]
      [view {:style {:width 50 :height 50 :backgroundColor "steelblue"}}]]

     [view
      [text-input {:style {:height 40} :placeholder "Type here to translate"}]
      [text {:style {:padding 10, :fontSize 42}} "🍕"]]
    ]
       ))

; ;;;;;;;;;;;funziona
(def routes [{:title "first route" :index 0} {:title "second route" :index 1}])
;;;;;;;;;;;;;examples navigator
; (defn app-root []
;   [navigator {:initial-route   (routes 0)
;                                 :render-scene (fn [route navigator]
;                                                 (r/as-element [text  (.-title route)]))
;                                 :style {:padding 100}
;                                             }])

; (defn app-root []
;   [navigator {:initial-route   {:title "first route" :index 0}
;               :render-scene (fn [route navigator]
;                               (r/as-element [view
;                                              [TouchableHighlight
;                                               {:on-press (fn [] (if (= (.-index route) 0) (.push navigator  #js {:title "second route" :index 1}) (.pop navigator)))}
;                                               [text (str "Hello " (.-title route))]]]))
;               :style {:padding 100}}])

(defn init []
  (.registerComponent app-registry "AwesomeProject" #(r/reactify-component app-root)))
