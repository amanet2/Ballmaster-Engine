engine_home="/Users/stallion/Code/Ballmaster-Engine"
game_home="/Users/stallion/Code/Ballmaster-Game"
java_bin="$engine_home/runtime/jdk-23.0.2.jdk/Contents/Home/bin"
java_out="$engine_home/tmp"
java_src="$engine_home/src/com/app/engine/*.java"
jar_name="$engine_home/engine.jar"

prep() {
  rm -f $jar_name
  rm -rf $java_out
}

build() {
  $java_bin/javac -d $java_out $java_src
  $java_bin/jar cf $jar_name -C $java_out .
}

cleanup() {
  rm -rf $java_out
  rm -f $game_home/$jar_name
  cp $jar_name $game_home
}

prep
build
cleanup
