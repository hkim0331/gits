#!/usr/bin/env bb

(ns gits)
(require '[babashka.fs :as fs])
(require '[babashka.process :as ps])

(defn usage
  "--help で呼ばれる。"
  []
  (println "# gits.clj

## SYNOPSIS
gits [options] [something] [dir]

dir 内の git dirs に対し、git something を実行する。
gits 単独では、`gits --parallel status .` のように働く。

## EXAMPLES
- gits --paralell status ~/projects
  ~/projects 内の複数の git フォルダの状態を並列にチェックする。

- gits -s fetch .
  作業ディレクトリ中の複数の git フォルダを逐次 fetch する。

- gits pull .
  作業ディレクトリ中の複数の git フォルダを並列に pull する。"))

(defn abbrev
  "もし、s が clean で終わっていたら clean を返す"
  [s]
  ;; (println "s=[" s "]")
  (if (re-find #"nothing to commit" s)
    "clean\n"
    s))

(defn git
  "ディレクトリを引数に取り、git verb を実行する関数を返す。"
  [verb]
  (fn [dir]
    (let [ret (ps/shell {:dir dir :out :string :err :string}
                        (str "git " verb))]
      (str dir " ... " (-> (:out ret) abbrev)))))

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
  ;;  (println "gits" opt verb dir)
  ;;  (println (git-dirs dir))
   (if (or (= opt "--serial") (= opt "-s"))
     (doall (map (git verb) (git-dirs dir)))
     (doall (pmap (git verb) (git-dirs dir))))))

(defn -main
  []
  (if (= "--help" (first *command-line-args*))
    (usage)
    (println (apply gits *command-line-args*))))

(-main)
