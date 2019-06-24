package com.github.xplosunn.tapirgen

import com.github.xplosunn.tapirgen.EndpointToGenerated.HttpCall

trait HttpCaller[L] {
  def imports: String
  def httpCall(httpCall: HttpCall[_, _, _], queryParameters: Map[String, String]): String
}
