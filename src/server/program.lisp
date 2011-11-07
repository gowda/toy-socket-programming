(in-package :cl-user)

(load "logging-p")
(load "logging")
(load "toy-server-p")
(load "toy-server")

(use-package :toy-server)

(toy-server/start 1025 "/tmp/toy-server-1025.log")
