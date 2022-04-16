from transcriber import load_model, transcribe
from gpt3 import text_analysis, translate
from preprocessing import convert_audio


def full_pipe(file_path,filename):
    #full audio analysis pipe

    #convert audio to wav
    convert_audio(file_path,filename)

    #import and save model (not needed if model is preloaded)
    model = load_model()

    #transcribe text
    transcribed_text = transcribe(f"path{filename[:-4]}.wav")

    #analyse text
    analysis = text_analysis(transcribed_text)

    #translate analysis
    translated_text = translate(analysis)

    return translated_text
