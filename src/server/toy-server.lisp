(in-package :toy-server)
;; simple lisp server program to listen on a port specified on command-line
;; and record the lines read from socket into a file, as specified on
;; command-line

(defun record-request (socket query file)

  (log-for networking "received request from host ~a~%"
           (socket-host/port socket))

  (if query
      ;; correct request, write to a file
      (with-open-file (stream file
                              :direction :output
                              :if-exists :append
                              :if-does-not-exist :create)
        (format stream "~a~%" query))))

(defun toy-server/start (port file)
  "run a server on `port'. `file' is the pathname to the file where output
must be written"
  (let ((server (open-socket-server port)))
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


;; (toy-server/start 1025 "/tmp/server-1025-lisp.log")