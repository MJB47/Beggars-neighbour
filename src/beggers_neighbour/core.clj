(ns beggars-neighbour.core)


(def player1 (atom []))
(def player2 (atom []))
(def pile (atom []))
(def turns (atom []))

(defn cards []
  (vec (range 52)))

(defn face-card? [card]
  (if-not (nil? card) 
    (if (< card 15)
      true
      false)))

(defn penalty [card]
  (cond
    (zero? (mod card 4))
      4
    (zero? (mod card 3))
      3
    (zero? (mod card 2))
      2
    :else
      1))

(defn give-pile [card]
  (swap! pile conj card)
  (swap! pile flatten)
  (swap! pile vec))

(defn clear-pile []
  (reset! pile []))

(defn give-p1 [card]
	(swap! player1 #(cons card %))
  (swap! player1 flatten)
  (swap! player1 vec)) ;flatten turns it into a list annoyingly


;; this is horrible and needs refactoring
(defn play-p1 
  ([]
  (if-not (face-card? (last @pile))
    (do
      (give-pile (last @player1))
      (swap! player1 pop)))
  (play-p1 (penalty (last @pile))))
  ([penalty]
    (if (zero? penalty)
      (do
        (give-p1 (swap! pile rseq))
        (clear-pile))      
      (do
        (if-not (empty? @player1)
          (do  
            (give-pile (last @player1))
            (swap! player1 pop)
        (if-not (face-card? (last @pile))
          (play-p1 (dec penalty)))))))))

(defn give-p2 [card]
	(swap! player2 #(cons card %))
  (swap! player2 flatten)
  (swap! player2 vec))

(defn play-p2 
  ([]
  (if-not (face-card? (last @pile))
    (do
      (give-pile (last @player2))
      (swap! player2 pop)))
  (play-p2 (penalty (last @pile))))
  ([penalty]
    (if (zero? penalty)
      (do
        (give-p2 (swap! pile rseq))
        (clear-pile))      
      (do
        (if-not (empty? @player2)
          (do  
            (give-pile (last @player2))
            (swap! player2 pop)
        (if-not (face-card? (last @pile))
          (play-p2 (dec penalty)))))))))

(defn clear-all []
  (reset! player1 [])
  (reset! player2 [])
  (reset! pile []))

(defn deal []
	(loop [cards (shuffle (cards))]
		(if (first cards)
      (do 
        (if (even? (count cards))
            (give-p1 (first cards))
            (give-p2 (first cards)))
          ;(println (count cards))
    (recur (rest cards))))))

(defn game []
  (loop [turn 0]
    (if-not (or (empty? @player1) (empty? @player2))
      (do
        (if (even? turn)
              (play-p1)
              (play-p2))
        ; (println (str "p1: " @player1))
        ; (println (str "p2: " @player2))
        ; (println (str "pile: " @pile))
        (recur (inc turn)))
      (do (swap! turns conj turn)
          (println (str "turns: " turn))))))

(defn game-loop [iter]
  (loop [iter iter]
    (if-not (zero? iter)
      (do
        (clear-all)
        (deal)
        (game)
        (recur (dec iter)))))
  (println (str "max: " (apply max @turns)))
  (println (str "min: " (apply min @turns)))
  (println (str "avg: " (/ (reduce + @turns) (count @turns)))))




