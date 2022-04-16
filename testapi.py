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


@app.get("/analyse_text")
def analyse_text(user_text):
    #text analysis pipe
    text = user_text

    analysis = text_analysis(text)

    #translate analysis
    translated_text = translate(analysis)

    return translated_text


@app.get("/analyse_audio")

def analyse_audio(audio_file,filename):

    #full audio analysis pipe

    #convert audio to wav
    convert_audio(audio_file,filename)

    #import and save model (not needed if model is preloaded)
    #model = load_model()

    #transcribe text
    transcribed_text = transcribe(f"{filename[:-4]}.wav")

    #analyse text
    analysis = text_analysis(transcribed_text)

    #translate analysis
    translated_text = translate(analysis)

    result = {
        'Transcribed text': transcribed_text,
        'Text analysis': translated_text
    }

    return result
  