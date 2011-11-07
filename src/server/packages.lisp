(in-package :cl-user)

(asdf:oos 'asdf:load-op :log5)
(asdf:oos 'asdf:load-op :port)

(defpackage :logging
  (:use :common-lisp :log5)
  (:export :networking))

(defpackage :toy-server
  (:use :common-lisp :logging :port)
  (:export :toy-server/start))