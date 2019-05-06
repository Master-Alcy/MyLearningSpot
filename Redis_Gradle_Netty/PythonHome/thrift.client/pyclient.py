# _*_ coding:utf-8 _*_

__author__ = 'author'

from course.thrift.py.generated import PersonService
from course.thrift.py.generated import ttypes

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol

try:
    tSocket = TSocket.TSocket('localhost', 8899)
    tSocket.setTimeout(600)

    transport = TTransport.TFramedTransport(tSocket)
    protocol = TCompactProtocol.TCompactProtocol(transport)
    client = PersonService.Client(protocol)

    transport.open()

    person = client.getPersonByUsername("ABC")
    print(person)

    newPerson = ttypes.Person()
    newPerson.username = "Python"
    newPerson.age = 99
    newPerson.married = False
    client.savePerson(newPerson)

    transport.close()

except Thrift.TException as tx:
    print('$s' % tx.message)