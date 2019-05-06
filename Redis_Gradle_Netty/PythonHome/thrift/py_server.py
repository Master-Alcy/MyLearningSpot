# _*_ coding:utf-8 _*_
__author__ = 'Jingxuan Ai'

from PersonServiceImpl import PersonServiceImpl
from course.thrift.py.generated import PersonService

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from thrift.server import TServer

try:
    personServiceHandler = PersonServiceImpl()
    processor = PersonService.Processor(personServiceHandler)

    serverSocket = TSocket.TServerSocket(host="127.0.0.1", port=8899)
    transportFactory = TTransport.TFramedTransportFactory()
    protocolFactory = TCompactProtocol.TCompactProtocolFactory()

    server = TServer.TSimpleServer(processor, serverSocket, transportFactory, protocolFactory);
    server.serve()

except Thrift.TException as ex:
    print('%s' % ex.message)