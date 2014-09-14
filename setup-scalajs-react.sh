#!/bin/bash

cd ../../
git clone git://github.com/japgolly/scalajs-react.git scalajs-react
cd scalajs-react
git checkout 669f87e9f2010f30929e5b2a09facd2872438c3b # known good version
sbt publish-local
cd ../gshakhn/private-wiki