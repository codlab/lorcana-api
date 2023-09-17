package com.github.codlab.lorcana.files

import dev.icerock.moko.resources.FileResource

expect suspend fun FileResource.readContent(): String
