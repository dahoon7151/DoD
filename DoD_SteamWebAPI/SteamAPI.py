import requests
from pymongo import MongoClient

# MongoDB 설정
MONGO_URI = "mongodb://localhost:27017/"
DB_NAME = "DoD_Game"
COLLECTION_NAME = "Steam_Game"

# Steam Web API URL
STEAM_API_URL = "https://api.steampowered.com/ISteamApps/GetAppList/v2/"

def fetch_steam_apps():
    try:
        response = requests.get(STEAM_API_URL)
        response.raise_for_status()  # HTTP 에러 발생 시 예외를 던짐
        data = response.json()
        return data.get("applist", {}).get("apps", [])
    except requests.exceptions.RequestException as e:
        print(f"스팀 API fetch 에러: {e}")
        return []

def save_to_mongodb(apps):
    try:
        # MongoDB 클라이언트 연결
        client = MongoClient(MONGO_URI)
        db = client[DB_NAME]
        collection = db[COLLECTION_NAME]

        # 기존 데이터 삭제 (옵션)
        collection.delete_many({})

        # MongoDB에 데이터 삽입
        if apps:
            collection.insert_many(apps)
            print(f"{len(apps)} records inserted into MongoDB.")
        else:
            print("No data to insert.")

    except Exception as e:
        print(f"스팀 API MongoDB 저장 에러: {e}")
    finally:
        client.close()

if __name__ == "__main__":
    # Steam API 호출 및 MongoDB 저장
    steam_apps = fetch_steam_apps()
    save_to_mongodb(steam_apps)
