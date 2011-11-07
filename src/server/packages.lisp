(in-package :cl-user)

(asdf:oos 'asdf:load-op :log5)

(defpackage :logging
  (:use :common-lisp :log5)
  (:export :networking))