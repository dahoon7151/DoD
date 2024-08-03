from pymongo import MongoClient

class Steam:
    def __init__(self):
        self.client = MongoClient('mongodb://localhost:27017/')
        self.db = self.client['DoD_Game']
        self.collection = self.db['Crawller_Steam']

    def insertMany(self, documents):
        print(documents)
        result = self.collection.insert_many(documents)

        if result.acknowledged:
            print("크롤링 데이터 DB 저장 성공")
            print("데이터 ID:", result.inserted_ids)
        else:
            print("크롤링 데이터 DB 저장 실패")

    def insertOne(self, item):
        self.collection.insert_one(item)

    def selectOne(self, id):
        self.collection.find_one(id)

    def selectList(self, condition=None):
        if condition is None:
            self.collection.find()
        else:
            self.collection.find(condition)
            
    def selectAll(self):
        self.collection.find()