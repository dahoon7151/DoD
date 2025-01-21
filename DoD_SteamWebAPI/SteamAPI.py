import requests
from pymongo import MongoClient

# MongoDB 설정
MONGO_URI = "mongodb://localhost:27017/"
DB_NAME = "DoD_Game"
COLLECTION_NAME = "Steam_Game"

# Steam Web API URL
STEAM_API_URL = "https://api.steampowered.com/ISteamApps/GetAppList/v2/"

def fetch_steam_apps():
    """Steam Web API에서 앱 데이터를 가져옵니다."""
    try:
        response = requests.get(STEAM_API_URL)
        response.raise_for_status()  # HTTP 에러 발생 시 예외를 던짐
        data = response.json()
        return data.get("applist", {}).get("apps", [])
    except requests.exceptions.RequestException as e:
        print(f"스팀 API fetch 에러: {e}")
        return []

def save_to_mongodb(apps):
    """MongoDB에 앱 데이터를 저장합니다."""
    try:
        # MongoDB 클라이언트 연결
        client = MongoClient(MONGO_URI)
        db = client[DB_NAME]
        collection = db[COLLECTION_NAME]

        # 이미 저장된 appid 확인
        existing_appids = set(doc["appid"] for doc in collection.find({}, {"appid": 1, "_id": 0}))

        # 새로운 앱만 필터링
        new_apps = [app for app in apps if app["appid"] not in existing_appids]

        # 새 데이터를 MongoDB에 삽입
        if new_apps:
            collection.insert_many(new_apps)
            print(f"{len(new_apps)} new records inserted into MongoDB.")
        else:
            print("No new data to insert.")

    except Exception as e:
        print(f"스팀 API MongoDB 저장 에러: {e}")
    finally:
        client.close()

if __name__ == "__main__":
    # Steam API 호출 및 MongoDB 저장
    steam_apps = fetch_steam_apps()
    save_to_mongodb(steam_apps)
