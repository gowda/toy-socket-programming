#!/usr/bin/sbcl --script

;; simple lisp server program to listen on a port specified on command-line
;; and record the lines read from socket into a file, as specified on
;; command-line
(mk:oos "port" :load)

(in-package :port)

(load "packages")
(load "logging")

(use-package :log5)
(use-package :logging)

(defun record-request (socket query file)
  ;; (format t "> received request from host ~a~%"
  ;;         (socket-host/port socket))

  (log-for networking "received request from host ~a~%"
           (socket-host/port socket))

  (if query
      ;; correct request, write to a file
      (with-open-file (stream file
                              :direction :output
                              :if-exists :append
                              :if-does-not-exist :create)
        (format stream "~a~%" query))))

(defun lisp-server (port file)
  "run a server on `port'. `file' is the pathname to the file where output
must be written"
  (let ((server (open-socket-server port)))
    ;; (format t "> started server on port ~d~%" port)
    (log-for networking "started server on port ~d~%" port)
    (unwind-protect
         (loop
            (let ((socket (socket-accept server)))
              (unwind-protect
                   (do ((line (read-line socket nil)
                              (read-line socket nil)))
                       ((null line))
                     (record-request socket
                                     line
                                     file))

                ;; close connection when done
                (close socket))))
      ;; close server before exiting
      (socket-server-close server))))


(lisp-server 1025 "/tmp/server-1025-lisp.log")