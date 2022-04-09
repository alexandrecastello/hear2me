from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from transcriber import load_model, transcribe
from gpt3 import text_analysis, translate
from preprocessing import convert_audio



app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allows all origins
    allow_credentials=True,
    allow_methods=["*"],  # Allows all methods
    allow_headers=["*"],  # Allows all headers
)


@app.get("/")
def index():
    return {"greeting": "Hello world"}


@app.get("/analyse")
def analyse(user_text):
    #text analysis pipe
    text = user_text

    analysis = text_analysis(text)

    #translate analysis
    translated_text = translate(analysis)

    return translated_text
