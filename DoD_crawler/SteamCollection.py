from pymongo import MongoClient, errors

class Steam:
    def __init__(self):
        self.client = MongoClient('mongodb://localhost:27017/')
        self.db = self.client['DoD_Game']
        self.collection = self.db['Crawler_Steam']
        self.collection.create_index([('steamId', 1)], unique=True)
        self.existing_ids = self._get_existing_ids()
    
    def _get_existing_ids(self):
        return set(doc['steamId'] for doc in self.collection.find({}, {'steamId': 1}))

    def insertMany(self, documents):
        new_documents = [doc for doc in documents if doc['steamId'] not in self.existing_ids]

        if new_documents:
            try:
                result = self.collection.insert_many(new_documents, ordered=False)
                print("크롤링 데이터 DB 저장 성공")
                print("데이터 ID:", result.inserted_ids)

                # 새로 삽입된 문서의 id를 existing_ids에 추가
                self.existing_ids.update(doc['steamId'] for doc in new_documents)
            except Exception as e:
                print("크롤링 데이터 DB 저장 중 알 수 없는 오류 발생:", e)
        else:
            print("삽입할 새로운 데이터가 없습니다.")

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