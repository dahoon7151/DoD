from selenium import webdriver
# from selenium.webdriver.chrome.options import Options
# from selenium.webdriver.chrome.service import Service
# from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys

import time
import logging
from SteamCollection import Steam

db = Steam()
log = logging.getLogger()
log.setLevel(logging.INFO)

# 크롬드라이버 자동 업데이트
# chrome_options = Options()
# chrome_options.add_experimental_option("detach", True) #브라우저 꺼짐 방지
# service = Service(executable_path=ChromeDriverManager().install())
# driver = webdriver.Chrome(service=service, options=chrome_options)
driver = webdriver.Chrome()

url = 'https://store.steampowered.com/search/?filter=topsellers&os=win'
driver.get(url)
wait = WebDriverWait(driver, 30)

img_selector = 'div.col.search_capsule > img'
rate_selector = 'div.responsive_search_name_combined > div.col.search_reviewscore.responsive_secondrow > span'

# 스크롤
scroll = driver.find_element(By.CSS_SELECTOR, 'body')
for i in range(20):
    wrap = wait.until(EC.presence_of_element_located((By.ID, "search_resultsRows")))
    game_info = wrap.find_elements(By.TAG_NAME,'a')
    log.info('a 태그 탐색 완료')

    element = wait.until(EC.visibility_of_element_located((By.CSS_SELECTOR, '#search_resultsRows > a:nth-child(1) > div.col.search_capsule > img')))
    ele_list = []
    if element:
        for game in game_info:
            ele = {}
            ele['id'] = game.get_attribute('data-ds-appid')
            if ele['id'] in db.existing_ids:
                continue  # 중복 체크
            ele['title'] = game.find_element(By.CLASS_NAME, 'title').text
            ele['url'] = game.get_attribute('href')
            try:
                ele['img'] = game.find_element(By.CSS_SELECTOR, img_selector).get_attribute('src')
            except:
                ele['img'] = "사진 없음"
            try:
                ele['release'] = game.find_element(By.CLASS_NAME, 'col.search_released.responsive_secondrow').text
            except:
                ele['release'] = "출시일 미정"
            try:
                ele['rate'] = game.find_element(By.CSS_SELECTOR, rate_selector).get_attribute('data-tooltip-html')
            except:
                ele['rate'] = "평가 없음"
            try:
                ele['price'] = game.find_element(By.CLASS_NAME, 'discount_final_price').text
            except:
                ele['price'] = "가격 미측정"
            try:
                ele['discount'] = game.find_element(By.CLASS_NAME, 'discount_pct').text
            except:
                ele['discount'] = "정가"

            ele_list.append(ele)

        if ele_list:
            db.insertMany(ele_list)
            db.existing_ids.update(ele['id'] for ele in ele_list) # 중복id 리스트 업데이트
        ele_list = []
        
    scroll.send_keys(Keys.END)
    wait.until(EC.presence_of_element_located((By.CSS_SELECTOR, 'a[data-ds-appid]'))) # time.sleep 도 고려
    

driver.quit()