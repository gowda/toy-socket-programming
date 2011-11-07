(in-package :logging)

(defcategory networking)

(defoutput networking-output "networking:")

(start-sender 'networking-log
              (stream-sender :location *error-output*)
              :category-spec '(networking)
              :output-spec '(networking-output message))

