from transcriber import load_model, transcribe
from gpt3 import text_analysis, translate
from preprocessing import convert_audio


def full_pipe(audio_file):
    #full audio analysis pipe

    #convert audio to wav
    convert_audio(audio_file)

    #import and save model
    model = load_model()

    #transcribe text
    transcribed_text = transcribe(f"{audio_file[:-4]}.wav")

    #analyse text
    analysis = text_analysis(transcribed_text)

    #translate analysis
    translated_text = translate(analysis)

    return translated_text
