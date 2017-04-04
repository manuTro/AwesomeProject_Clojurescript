(ns awesome-project.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))


(defn app-root[]
  (fn []
         [text {:style {:color "red" :text-align "center"}}"Hello world!"]
          ))

(defn init []
      (.registerComponent app-registry "AwesomeProject" #(r/reactify-component app-root)))
