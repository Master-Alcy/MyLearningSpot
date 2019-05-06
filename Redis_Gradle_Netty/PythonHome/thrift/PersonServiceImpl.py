# _*_ coding:utf-8 _*_
__author__ = 'Jingxuan Ai'

from course.thrift.py.generated import ttypes

class PersonServiceImpl:

    def getPersonByUsername(self, username):
        print("Got client params: " + username)

        person = ttypes.Person()
        person.username = username
        person.age = 30
        person.married = False

        return person

    def savePerson(self, person):
        print("Got client params: ")
        print(person.username)
        print(person.age)
        print(person.married)