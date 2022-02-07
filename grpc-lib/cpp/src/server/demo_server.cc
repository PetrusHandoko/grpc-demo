/*
 *
 * Copyright 2015 gRPC authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

#define BOOST_LOG_DYN_LINK 1

#include <iostream>
#include <memory>
#include <string>
#include <unordered_map>

#include <boost/log/trivial.hpp>
#include <boost/log/utility/setup.hpp>

#include <grpcpp/ext/proto_server_reflection_plugin.h>
#include <grpcpp/grpcpp.h>
#include <grpcpp/health_check_service_interface.h>

#include "UserInfoService.grpc.pb.h"


using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;
using grpclib::UserInfoService;
using grpclib::UserInfoUpdateResponse;
using grpclib::UserInfoUpdateRequest;

using namespace std ;


static std::unordered_map< std::string , UserInfoUpdateRequest> storage ;

static void init_log()
{
    static const std::string COMMON_FMT("[%TimeStamp%][%Severity%]:  %Message%");
    boost::log::register_simple_formatter_factory< boost::log::trivial::severity_level, char >("Severity");
    // Output message to file, rotates when file reached 1mb or at midnight every day. Each log file
    //
    boost::log::add_file_log (
		 boost::log::keywords::file_name = "user_info_%3N.log",
		 boost::log::keywords::rotation_size = 1 * 1024 * 1024,
		 boost::log::keywords::max_size = 20 * 1024 * 1024,
		 boost::log::keywords::time_based_rotation = boost::log::sinks::file::rotation_at_time_point(0, 0, 0),
		 boost::log::keywords::format = COMMON_FMT,
		 boost::log::keywords::auto_flush = true ) ;
    boost::log::add_common_attributes();

}

// Logic and data behind the server's behavior.
class DemoServiceImpl final : public UserInfoService::Service {

  void logUserInfo( const UserInfoUpdateRequest * request ) {

    // log it
    	BOOST_LOG_TRIVIAL(info)  << request->firstname() << ": " 
    	 << request->lastname() << ":" 
    	 << request->dob() << ":" 
    	 << request->email() << ":" 
    	 << request->timestamp() ;

  }

  Status SendUpdate(ServerContext* context, const UserInfoUpdateRequest* request,
                  UserInfoUpdateResponse* reply) override {

    std::string hashkey = request->timestamp();
    storage.insert(std::make_pair(hashkey, *request)) ;

    // Prepare for the output.
    reply->set_timestamp( hashkey );
    reply->set_status("Created");
    std::cout << "SendUpdate with time stamp " << hashkey << "\n"  ;
    // log it
    std::cout << request->firstname() << " " ;
    std::cout << request->lastname() << " " ;
    std::cout << request->dob() << " " ;
    std::cout << request->email() << " " ;
    std::cout << request->timestamp() << "\n"  ;

    logUserInfo ( request ) ;

    return Status::OK;
  }

};

void RunServer() {
  std::string server_address("0.0.0.0:50051");
  DemoServiceImpl service;

  grpc::EnableDefaultHealthCheckService(true);
  grpc::reflection::InitProtoReflectionServerBuilderPlugin();
  ServerBuilder builder;
  // Listen on the given address without any authentication mechanism.
  builder.AddListeningPort(server_address, grpc::InsecureServerCredentials());
  // Register "service" as the instance through which we'll communicate with
  // clients. In this case it corresponds to an *synchronous* service.
  builder.RegisterService(&service);
  // Finally assemble the server.
  std::unique_ptr<Server> server(builder.BuildAndStart());
  std::cout << "Server listening on " << server_address << std::endl;

  // Wait for the server to shutdown. Note that some other thread must be
  // responsible for shutting down the server for this call to ever return.
  server->Wait();
}

int main(int argc, char** argv) {
  init_log();
  RunServer();

  return 0;
}
