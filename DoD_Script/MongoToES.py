from pymongo import MongoClient
from elasticsearch import Elasticsearch
from elasticsearch.helpers import bulk

# MongoDB 연결
mongo_client = MongoClient("mongodb://localhost:27017")
mongo_db = mongo_client["DoD_Game"]
summary_collection = mongo_db["Game_Summary"]

# Elasticsearch 연결
es = Elasticsearch("http://localhost:9200")

def generate_documents():
    for doc in summary_collection.find():
        if not doc.get("appid") or not doc.get("name"):
            continue

        yield {
            "_index": "game",
            "_source": {
                "appid": doc["appid"],
                "name": doc["name"]
            }
        }

# 색인 실행
bulk(es, generate_documents())

# 추후에 파이프라인 구축할 때
# ES에 새 document 생성 X
# 새로 추가되는 데이터만 추가하도록 구현