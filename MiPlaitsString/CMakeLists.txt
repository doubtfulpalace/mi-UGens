cmake_minimum_required (VERSION 3.8)
include("${CMAKE_CURRENT_SOURCE_DIR}/../MiPlaitsCommon/CMakeTemplate.txt")
project (MiPlaitsString)

set(BUILD_SOURCES
	MiPlaitsString.cpp
)

add_library(${PROJECT_NAME} MODULE
        ${STMLIB_SOURCES}
        ${MI_SOURCES}
        ${BUILD_SOURCES}
)

set(CMAKE_SHARED_MODULE_PREFIX "")
if(APPLE OR WIN32)
set(CMAKE_SHARED_MODULE_SUFFIX ".scx")
endif()

if(APPLE)
# create groups in our project
source_group(TREE ${MUTABLE_PATH} FILES ${STMLIB_SOURCES} ${MI_SOURCES})

target_link_libraries(${PROJECT_NAME} PUBLIC "-framework Accelerate")
endif()
