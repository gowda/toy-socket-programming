(in-package :cl-user)

(asdf:oos 'asdf:load-op :log5)
(asdf:oos 'asdf:load-op :port)

(load "packages")
(load "logging")
(load "toy-server")

(use-package :toy-server)

(toy-server/start 1025 "/tmp/toy-server-1025.log")
