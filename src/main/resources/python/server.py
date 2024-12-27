import requests
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

class gLCP(BaseModel):
    key: str

@app.post("/gardening_LoadCloudPreset")
async def gardening_LoadCloudPreset(key: gLCP):
    key = key.key
    response = requests.get(
        "https://raw.githubusercontent.com/luna724/lcc/refs/heads/main/lcg_cli-import_official-cloudkey_and_files.json"
    )
    if response.status_code != 200:
        return ""
    data_json = response.json()
    available_keys = data_json["availables"]
    main_url = data_json["url"]

    if key.lower() in available_keys:
        try:
            target_json = data_json[key]
        except KeyError:
            print("Key not found in jsondata but available. please report this error.")
            return "FATAL ERROR - KEY NOT FOUND BUT AVAILABLE"
        target_url = main_url + target_json

        response = requests.get(target_url)
        if response.status_code != 200:
            return ""
        return response.json()

    return ""