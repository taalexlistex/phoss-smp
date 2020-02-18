#!/bin/bash
#
# Copyright (C) 2015-2020 Philip Helger and contributors
# philip[at]helger[dot]com
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


version=5.2.2

docker login --username phelger

# --------------- XML -----------------------

docker push phelger/smp:$version
docker push phelger/smp:latest
docker push phelger/phoss-smp-xml:$version
docker push phelger/phoss-smp-xml:latest

# --------------- SQL -----------------------

docker push phelger/phoss-smp-sql:$version
docker push phelger/phoss-smp-sql:latest

# --------------- MongoDB -----------------------

docker push phelger/phoss-smp-mongodb:$version
docker push phelger/phoss-smp-mongodb:latest

# --------------- finalize

docker logout
