(in-package :cl-user)

(defpackage :logging
  (:use :common-lisp :log5)
  (:export :networking))

(defpackage :toy-server
  (:use :common-lisp :log5 :logging :port)
  (:export :toy-server/start))