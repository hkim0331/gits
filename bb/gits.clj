#!/usr/bin/env bb
;;
;; ## SYNOPSIS
;;
;; gits [options] [something] [dir]
;;
;; dir 内の git dirs に対し、git something を実行する。
;;
;; デフォルトは、
;;
;; gits --parallel status .
;;
;; ## examples
;;
;; ~/projects 内の複数の git フォルダの状態を並列にチェックする。
;;
;;     % gits --paralell status ~/projects
;;
;; 作業ディレクトリ中の複数の git フォルダを逐次 fetch する。
;;
;;     % gits -s fetch .
;;
;; 作業ディレクトリ中の複数の git フォルダを並列に pull する。
;;
;;     % gits pull
;;
(ns gits)
(require '[babashka.fs :as fs])
(require '[babashka.process :as ps])

(defn git
  "ディレクトリを引数に取り、git verb を実行する関数を返す。"
  [verb]
  (fn [dir]
    (-> (ps/shell {:dir dir :out :string :err :string}
                  (str "git " verb))
        :out)))

(comment
  ((git "status") ".")
  :rcf)

(defn git-dir?
  "dir が git 配下かを dir/.git が存在するかで判定する。"
  [dir]
  (fs/exists? (str dir "/.git")))

(comment
  (git-dir? ".")
  (git-dir? "/Users/hkim/ramdisk")
  :rcf)

(defn git-dirs
  "dir 以下の subdir で、git 配下のディレクトリだけを返す。"
  [dir]
  (filter git-dir? (fs/list-dir (fs/expand-home dir))))

(comment
  (git-dirs "/Users/hkim")
  (git-dirs "/Users/hkim/projects")
  (git-dirs "~/projects")
  :rcf)

(defn gits
  ([] (gits "."))
  ([dir] (gits "status" dir))
  ([verb dir] (gits "--parallel" verb dir))
  ([opt verb dir]
   (if (or (= opt "--serial") (= opt "-s"))
     (doall (map (git verb) dir))
     (doall (pmap (git verb) dir)))))
