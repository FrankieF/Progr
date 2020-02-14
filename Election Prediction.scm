; Frankie Fasola
; Programming Languages - Scheme Homework
(define (regions 
				("ME" "NH" "VT" "MA" "CT" "RI")
				("NY" "PA" "NJ" "DE" "MD")
				("VA" "NC" "SC" "GA" "FL" "AL" "MS" "TN" "KY" "WV" "AR" "LA")
				("OH" "MI" "IN" "IL" "WI" "MN")
				("IA" "MO" "ND" "SD" "NE" "KS" "OK" "TX")
				("MT" "WY" "CO" "NM" "AZ" "UT" "ID" "NV")
				("WA" "OR" "CA" "AK" "HI")))
;(define newengland ("ME" "NH" "VT" "MA" "CT" "RI"))
;(define northeast ("NY" "PA" "NJ" "DE" "MD"))
;(define southeast ("VA" "NC" "SC" "GA" "FL" "AL" "MS" "TN" "KY" "WV" "AR" "LA"))
;(define lakes("OH" "MI" "IN" "IL" "WI" "MN"))
;(define central("IA" "MO" "ND" "SD" "NE" "KS" "OK" "TX"))
;(define west ("MT" "WY" "CO" "NM" "AZ" "UT" "ID" "NV"))
;(define pacific ("WA" "OR" "CA" "AK" "HI"))

;(define (predict state L)
;				((cond (null? x)									  0)
;				(+	(cond (= state (car(cdr L)))		50)
;						((car(cdr(cdr L))) 					% 10)
;						((car(cdr(cdr L))))
;						(* (car(cdr(cdr(cdr L)))) 			  5)
;					(cons )
;				
;				)))

(define (predict state L)
				(cons (car L) (+ (checkRegions state (car(cdr L))) 
												(checkState (car(cdr L)))
												(addCommericals (car(cdr(cdr L)))
												(addPrimaries (car(cdr(cdr(cdr L))))))))
)				

(define (checkRegionandState state _state)
				(+ 
					(cond (eqv? (getRegion state regions) (getRegion _state regions) 20)
					(else 0))
					(cond (eqv? state _state)		50)
					(else 0))
)

(define (checkRegions state _state)
				(cond (eqv? (getRegion state regions) (getRegion _state regions))					20))

(define (getRegion state L)
				(cond (null? L)									0)
				(cond (eqv? state (car(car L)))			1)
				(else (+ 1 (getRegion state (cdr L))))
)

(define (checkState state _state)
				(cond (eqv? state _state)					50)
				(else 													0))

(define (addCommericals commericals)				(commericals % 10))

(define (addPrimaries primaries)							(* primaries 5))
				
				
				
				
				