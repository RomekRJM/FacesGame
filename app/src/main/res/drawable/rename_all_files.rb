require 'fileutils'
require 'shellwords'

Dir.glob('./*'){
  |file|
  if File.stat(file).file?
      newFile = "face_" + File.basename(file)
      FileUtils.mv(file, newFile)
  end
}