# gits.clj

複数の git 管理されたプロジェクトを一つのフォルダにまとめている時、
一度に、パラレルに、git status や git pull を実行したい。

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
  作業ディレクトリ中の複数の git フォルダを並列に pull する。


```shell
m24~/projects(:|✔)
% time ./gits.clj --parallel pull .
(./py99 ... Already up to date.
 ./2023-python ... Already up to date.
 ./docker ... Already up to date.
 ./dotfiles ... Already up to date.
 ./the-little-schemer ... Already up to date.
 ./qa ... Already up to date.
 ./clj-repl ... Already up to date.
 ./scratch ... Already up to date.
 ./l22 ... Already up to date.
 ./typing-ex ... Already up to date.
 ./wil ... Already up to date.
 ./jtask ... Already up to date.
)
./gits.clj pull .  0.58s user 0.30s system 38% cpu 2.266 total

m24~/projects(:|✔)
% time ./gits.clj --serial pull .
(./py99 ... Already up to date.
 ./2023-python ... Already up to date.
 ./docker ... Already up to date.
 ./dotfiles ... Already up to date.
 ./the-little-schemer ... Already up to date.
 ./qa ... Already up to date.
 ./clj-repl ... Already up to date.
 ./scratch ... Already up to date.
 ./l22 ... Already up to date.
 ./typing-ex ... Already up to date.
 ./wil ... Already up to date.
 ./jtask ... Already up to date.
)
./gits.clj --serial pull .  0.88s user 0.39s system 6% cpu 19.731 total
```
