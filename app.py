from transcriber import load_model, transcribe
from gpt3 import text_analysis, translate
from preprocessing import convert_audio


def full_pipe(file_path,filename):
    #full audio analysis pipe

    #convert audio to wav and transcribe
    text,proba=convert_audio(file_path,filename)

    #analyse text
    analysis = text_analysis(text)

    #translate analysis
    translated_text = translate(analysis)

    return translated_text
