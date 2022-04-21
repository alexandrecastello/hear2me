from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
# from transcriber import transcribe
# from transcriber import load_model
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
    return {"Esta API foi desenvolvida por alunos do Bootcamp de Data Science da Le Wagon. :D"}


# @app.get("/analyse_text")
# def analyse_text(user_text):
#     #text analysis pipe
#     text = user_text

#     analysis = text_analysis(text)

#     #translate analysis
#     translated_text = translate(analysis)

#     return translated_text


@app.get("/analyse_audio")

def analyse_audio(audio_file,filename):

    #full audio analysis pipe

    #convert audio to wav and transcribe
    transcribed_text, proba = convert_audio(audio_file, filename)

    transcribed_text+="."

    transcribed_text=translate(transcribed_text, 'en')

    #analyse text
    analysis = text_analysis(transcribed_text)

    transcribed_text = translate(transcribed_text)

    #translate analysis
    translated_text = translate(analysis)

    result = {
        'transcribedText': transcribed_text,
        'textAnalysis': translated_text
    }

    return result
